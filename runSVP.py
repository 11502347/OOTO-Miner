'''
Sample python code in running SampleVsPopulation.py

'''
import sys
import SampleVsPopulation as svp

print sys.argv

populationDataset = sys.argv[2]
sampleFeature = sys.argv[3]
selectedFeature = sys.argv[4]
allValues = sys.argv[5]
selectedValues = sys.argv[6]
zCriticalValue = 1.96


svp.sampleVsPopulation(populationDataset, sampleFeature, selectedFeature, allValues, selectedValues, zCriticalValue)
