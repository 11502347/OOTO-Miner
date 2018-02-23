# -*- coding: utf-8 -*-
"""
Created on Tue Feb 20 11:03:29 2018

@author: Rgee
"""

import sys
import csv
import copy
from collections import Counter
import math

#Format of console input: %run ./SampleVsPopulation.py <Path of Population Dataset> <Feature to get Samples by> <Feature B to focus on> <All values of feature B> <Selected values of Feature B> <Z Critical Value>

'''
Reads all of the rows in a csv file as a list of dictionaries
'''
def readCSVDict(filename):
    rows = csv.DictReader(open(filename))
    return rows

'''
Writes a set of rows into a .csv file given the filename
'''
def writeOnCSV(rows, filename):
	with open(filename, 'wb') as f:
	    writer = csv.writer(f)
	    writer.writerows(rows)
	print 'Saved file is: ' + filename

'''
Given the feature you want to make the samples by, divide the population into the samples.

Example: If the sample feature is 'age'


'''
def makeSamples(records, sampleFeature,sampleGroups):
    groupNames = []
    for record in records:
        if(record[sampleFeature] not in groupNames):
            groupNames.append(record[sampleFeature])

    for gn in groupNames:
        sample = {'Sample Name':gn, 'Total':0, 'Frequency':0}
        sampleGroups.append(sample)

        

def getSampleTotalsAndProportions(records, samples, sampleFeature, selectedFeature, allValues, selectedValues):
    for record in records:
        for sample in samples:
            if(record[sampleFeature] == sample['Sample Name']):
               if(record[selectedFeature] in allValues):
                   sample['Total'] += 1
               if(record[selectedFeature] in selectedValues):
                   sample['Frequency'] += 1
               break
    for sample in samples:
        sample['Proportion'] = float(sample['Frequency']) / float(sample['Total'])

        
            
def getPopTotalsAndProportions(records,selectedFeature,allValues,selectedValues):
    population = {'Total':0, 'Frequency':0}
    for record in records:
        if(record[selectedFeature] in allValues):
            population['Total'] += 1
        if(record[selectedFeature] in selectedValues):
            population['Frequency'] += 1
    population['Proportion'] = float(population['Frequency']) / float(population['Total'])
    return population

def getStandardError(p,n):
    return math.sqrt( (p * (1-p)) / float(n))

def getZScore(sample, population):
    se = getStandardError(population['Proportion'], sample['Total'])

    z = (sample['Proportion'] - population['Proportion']) / se

    return z,se

def makeResults(header, samples, population, zCriticalValue, fileName):
    rows = []
    rows.append(header)
    for sample in samples:
        tempRow = []
        tempRow.append(population['Total'])
        tempRow.append(population['Frequency'])
        tempRow.append(population['Proportion'])
        tempRow.append(sample['Sample Name'])
        tempRow.append(sample['Total'])
        tempRow.append(sample['Frequency'])
        tempRow.append(sample['Proportion'])
        tempRow.append(sample['Standard Error'])
        tempRow.append(zCriticalValue)
        tempRow.append(sample['Lower Bound'])
        tempRow.append(sample['Upper Bound'])
        tempRow.append(sample['Accept or Reject'])
        rows.append(tempRow)
    
    writeOnCSV(rows, fileName)


def sampleVsPopulation(popDatasetPath, sampleFeature, selectedFeature, allValues, selectedValues, zCriticalValue):
    delimiter = ':'

    records = readCSVDict(popDatasetPath) #Read records from the population dataset file

    samples = []

    makeSamples(records, sampleFeature,samples)#Make the samples from the population dataset

    records = readCSVDict(popDatasetPath) #Read the records again because for some reason they disappear after making the sample groups

    getSampleTotalsAndProportions(records, samples, sampleFeature, selectedFeature, allValues, selectedValues)#Calculate n, f, and p of the samples

    records = readCSVDict(popDatasetPath) #Read the records again because for some reason they disappear after making the sample groups

    population = getPopTotalsAndProportions(records,selectedFeature,allValues,selectedValues)#Make the population and calculate N, F, and P of the population

    for sample in samples:
        #Perform Z-Test against population
        zScore, standardError = getZScore(sample,population)#Retrieve the z-score of the sample and the standard error to the population
        sample['Z'] = zScore
        sample['Standard Error'] = standardError
        sample['Upper Bound'] = sample['Proportion'] + (zCriticalValue * sample['Standard Error'])
        sample['Lower Bound'] = sample['Proportion'] - (zCriticalValue * sample['Standard Error'])
        if(population['Proportion'] >= sample['Lower Bound'] and population['Proportion'] <= sample['Upper Bound']):
            sample['Accept or Reject'] = 'Accept'
        else:
            sample['Accept or Reject'] = 'Reject'

    

    header = ['N','F','P','Sample','n','f','p','SE','Z','LB','UB','Accept/Reject']
    saveFile = 'Sample vs Population_'+sampleFeature+'_'+selectedFeature+'.csv'
    makeResults(header, samples, population, zCriticalValue, saveFile)

    return population, samples






