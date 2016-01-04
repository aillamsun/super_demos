package hadoop2_6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer>{
	
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		//生成一个计算任务 负责计算 1 + 2 + 3 + 4
		CountTask task = new CountTask(1, 4);
		
		Future<Integer> result = forkJoinPool.submit(task);
		try {
			System.out.println(result.get());
		} catch (Exception e) {
		}
	}
	
	
	private static final int THRESHOLD = 2;//阀值
	private int start;
	private int end;
	
	
	public CountTask(int start,int end){
		this.end = end;
		this.start = start;
	}
	
	
	
	@Override
	protected Integer compute() {
		int sum = 0;
		//如果任务足够小就计算任务
		boolean canCompute = (end - start) <= THRESHOLD;
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		}else {
			//如果任务阀值大于阀值 就分裂成两个子任务计算
			int middle = (start - end) / 2;
			CountTask leftTask = new CountTask(start, end);
			CountTask rightTask = new CountTask(middle - 1, end);
			//执行子任务
			leftTask.fork();
			rightTask.fork();
			
			//等待子任务执行完，并得到结果
			int leftResult = leftTask.join();
			int rightReult = rightTask.join();
			
			sum = leftResult + rightReult;
		}
		return sum;
	}
}
