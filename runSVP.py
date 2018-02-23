'''
Sample python code in running SampleVsPopulation.py

'''

import SampleVsPopulation as svp



populationDataset = "C:/Users/Rgee/Google Drive/Classroom/PRC-ST1 Batch 115/Deployment/OOTO UNICEF/[CSC713M] OOTO/Datasets/ready for OOTO/nation.csv"
sampleFeature = 'a2'
selectedFeature = 'a24b'
allValues = '1:2:3:4'
selectedValues = '2:3'
zCriticalValue = 1.96

svp.sampleVsPopulation(populationDataset, sampleFeature, selectedFeature, allValues, selectedValues, zCriticalValue)
