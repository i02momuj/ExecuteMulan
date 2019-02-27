package executeMulan.mlc;
import executeMulan.ExecuteMulanAlgorithm;
import mulan.classifier.meta.EnsembleOfSubsetLearners;
import mulan.classifier.transformation.LabelPowerset;
import mulan.data.ConditionalDependenceIdentifier;
import weka.classifiers.trees.J48;


public class ExecuteCDE extends ExecuteMulanAlgorithm {
	
	public void execute (String tvalue, String Tvalue, String xvalue, String ovalue, boolean lvalue, int nIter, int fvalue)
	{		
		 try{
			 prepareExecution(tvalue, Tvalue, xvalue, ovalue, fvalue);
			 
			 EnsembleOfSubsetLearners learner = null;
            
			 /* The seeds are 10, 20, 30, ... */        	   
			 for(int i=1; i<=nIter; i++)
			 {
				 time_in = System.currentTimeMillis();
				 learner = new EnsembleOfSubsetLearners(new LabelPowerset(new J48()), new J48(), new ConditionalDependenceIdentifier(new J48()), 10);  
				 learner.setSeed(i*10);
	        	 learner.setNumOfRandomPartitions(10000);

	        	 measures = prepareMeasuresClassification(trainingSet);    	
	         	
	         	if(nFolds > 0) {
	         		mResults = eval.crossValidate(learner, trainingSet, measures, nFolds);
	         	}
	         	else {
	         		learner.build(trainingSet);  
	         	    results = eval.evaluate(learner, testSet, measures);
	         	}
	    	       
	         	time_fin = System.currentTimeMillis();
	    	      
	    	    total_time = time_fin - time_in;
	
	    	    System.out.println("Execution time (ms): " + total_time);

	    	    //Print header only in first iteration
	    	    if(i == 1) {
	    	    	printHeader(lvalue);
	    	    }
	    	    
	    	    if(nFolds <= 0) {
	    	    	printResults(Tvalue, lvalue, "CDE");
	    	    }
	    	    else {
	    	    	printResultsCV(tvalue, lvalue, "CDE");
	    	    }
	    	    
			 }//End for
			 
		}
        catch(Exception e1)
    	{
    		e1.printStackTrace();
    	}
    	finally{
    		if(pw != null)
    		{
    			pw.close();
    		}
    	}      
	}
}
