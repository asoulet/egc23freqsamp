package sampler;


import java.util.AbstractCollection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public abstract class FrequentSampler implements Sampler {
	
	protected int k = 10;
	protected AbstractCollection<Pattern> sample = new ArrayList<Pattern>();

	protected Dataset dataset;
	protected long totalTime = 0;
	protected double total = 0;
	protected float threshold;
	
	public FrequentSampler(Dataset dataset, int k, float threshold) {
		this.threshold = threshold;
		this.dataset = dataset;
		this.k = k;
		totalTime = System.nanoTime();
		prepare();
		compute();
		totalTime = System.nanoTime() - totalTime;
	}
	
	public final double getTotal() {
		return total;
	}

	public final long getTotalTime() {
		return totalTime;
	}

	protected abstract void prepare();

	private final void compute() {
		int absolute = (int) Math.ceil(threshold * dataset.getTransactionNumber());
		for (int i = 0; i < k; i++) {
			Itemset itemset = null;
			do {
				itemset = drawPattern();
				dataset.support(itemset);
			} while (itemset.getCount() < absolute);
		}
	}

	protected abstract Itemset drawPattern();


	@Override
	public int getK() {
		return k;
	}

	@Override
	public AbstractCollection<Pattern> getSample() {
		return sample;
	}

	public int getAbsolute() {
		return (int) Math.ceil(threshold * dataset.getTransactionNumber());
	}

	public int getSize() {
		return dataset.getSize();
	}


}
