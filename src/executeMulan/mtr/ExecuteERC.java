package executeMulan.mtr;
import executeMulan.ExecuteMulanAlgorithm;
import mulan.regressor.transformation.EnsembleOfRegressorChains;
import mulan.regressor.transformation.RegressorChain;

public class ExecuteERC extends ExecuteMulanAlgorithm {
	
	public void execute (String tvalue, String Tvalue, String xvalue, String ovalue, boolean lvalue, int nIter, int fvalue)
	{		
		 try{
			 prepareExecution(tvalue, Tvalue, xvalue, ovalue, fvalue);
			 
			 EnsembleOfRegressorChains learner = null;
            
			 /* The seeds are 10, 20, 30, ... */        	   
			 for(int i=1; i<=nIter; i++)
			 {
				time_in = System.currentTimeMillis();
				 
				learner = new EnsembleOfRegressorChains();
	        	learner.setSeed(i*10);
	        	learner.setMeta(RegressorChain.metaType.INSAMPLE);
  	
	        	if(nFolds > 0) {
	        		mResults = eval.crossValidate(learner, trainingSet, nFolds);
	        		for(int m=0; m<mResults.getEvaluations().get(0).getMeasures().size(); m++) {
	        			measures.add(mResults.getEvaluations().get(0).getMeasures().get(m));
	        		}
	        	}
	        	else {
	        		learner.build(trainingSet);
	        		measures = prepareMeasuresRegression(trainingSet, testSet);
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
	    	    	printResults(Tvalue, lvalue, "ERC");
	    	    }
	    	    else {
	    	    	printResultsCV(tvalue, lvalue, "ERC");
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
