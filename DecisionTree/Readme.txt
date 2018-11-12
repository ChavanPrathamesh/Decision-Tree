Implementing the decision tree learning algorithm using the information gain heuristic
and variance impurity heuristic 

Assuming you are in the directory containing this README:

## To clean:
ant -buildfile decisiontree/src/build.xml clean

-----------------------------------------------------------------------
## To compile: 
ant -buildfile decisiontree/src/build.xml all

-----------------------------------------------------------------------
## To run by specifying arguments from command line 
## We will use this to run your code:

ant -buildfile decisiontree/src/build.xml run -Darg0=/home/pchavan4/spring18/ml/assignment1/prathamesh_chavan_hw1/dataset_2/training_set.csv -Darg1=/home/pchavan4/spring18/ml/assignment1/prathamesh_chavan_hw1/dataset_2/validation_set.csv -Darg2=/home/pchavan4/spring18/ml/assignment1/prathamesh_chavan_hw1/dataset_2/training_set.csv -Darg3=yes -Darg4=yes

NOTE: Please make sure that use this command as specified above and with correct arguments
Any deletion or addition of spaces and this will not work
For:
-Darg0= Path of training file
-Darg1= Path of validation file
-Darg2= Path of test file
-Darg3= toprint {yes,no}
-Darg4= toprune{yes,no}

-----------------------------------------------------------------------

## To create tarball for submission
ant -buildfile src/build.xml tarzip or tar -zcvf firstName_secondName_assign_number.tar.gz firstName_secondName_assign_number

-----------------------------------------------------------------------

OUTPUT:
It outputs the accuracies on the test set for decision trees constructed using the two heuristics. 
If to-print equals yes, it will print the decision tree to the standard output. 


