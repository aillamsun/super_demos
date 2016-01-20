package com.wy.hadoop.sixteen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class JobMain extends Configuration implements Tool {

	private String input1 = null;
	private String input2 = null;
	private String output = null;
	
	@Override
	public Configuration getConf() {
		return new Configuration();
	}


	@Override
	public void setConf(Configuration arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int run(String[] arg0) throws Exception {
		setArgs(arg0);
		checkParam();
		
		Configuration configuration = new Configuration();
		Job job = new Job(configuration,"multiple_input2_job");
		job.setJarByClass(JobMain.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		
		job.setReducerClass(MultipleReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		MultipleInputs.addInputPath(job, new Path(input1), FirstInputFormat.class, FirstMapper.class);
		MultipleInputs.addInputPath(job, new Path(input2), SecondInputFormat.class, SecondMapper.class);
		
		
		Path path = new Path(output);
		FileSystem fs = FileSystem.get(configuration);
		if(fs.exists(path)){
			fs.delete(path, true);
		}
		FileOutputFormat.setOutputPath(job, path);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
		return 0;
	}

	private void checkParam(){
		if(input1==null || "".equals(input1.trim())){
			System.out.println(" no input phone-data path! ");
			userMaunel();
			System.exit(-1);
		}
		if(input2==null || "".equals(input2.trim())){
			System.out.println(" no input user-data path! ");
			userMaunel();
			System.exit(-1);
		}
		if(output==null || "".equals(output.trim())){
			System.out.println(" no output path! ");
			userMaunel();
			System.exit(-1);
		}
	}
	
	private void userMaunel(){
		
		System.err.println("Usage:");
		System.err.println("-i1 input \t phone data path.");
		System.err.println("-i2 input \t user data path.");
		System.err.println("-o output \t output data path.");
		
	}
	
	
	//-i1 xxx -i2 xxx -o xxx -delimiter x
	private void setArgs(String[] args){
		for(int i=0;i<args.length;i++){
			if("-i1".equals(args[i])){
				input1 = args[++i];
			}
			if("-i2".equals(args[i])){
				input2 = args[++i];
			}
			if("-o".equals(args[i])){
				output = args[++i];
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		Configuration configuration = new Configuration();
		ToolRunner.run(configuration, new JobMain(), args);

	}

}
