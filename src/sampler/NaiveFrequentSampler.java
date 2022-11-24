package sampler;

public class NaiveFrequentSampler extends FrequentSampler {
	
	private double weights [] = null;
	
	public NaiveFrequentSampler(Dataset dataset, int k, float threshold) {
		super(dataset, k, threshold);
	}
	
	protected void prepare() {
		dataset.removeNonFrequentItems(threshold);
		weights = new double [dataset.getTransactionNumber()];
		for (int t = 0; t < dataset.getTransactionNumber(); t++) {
			total += Math.pow(2, dataset.getTransactionLength(t));
			weights[t] = total;
		}
	}

	protected Itemset drawPattern() {
		double value = Math.random() * total;
		int left = 0;
		int right = weights.length;
		int middle = (left + right) / 2;
		while ((middle > 0 && weights[middle - 1] >= value) || weights[middle] < value) {
			if (weights[middle] < value)
				left = middle + 1;
			else
				right = middle - 1;
			middle = (left + right) / 2;
		}
		return drawPattern(middle);		
	}

	protected Itemset drawPattern(int t) {
		boolean[][] matrix = dataset.getMatrix();
		Itemset itemset = new Itemset();
		for (int i = 1; i <= dataset.getItemNumber(); i++)
			if (matrix[t][i] && Math.random() < 0.5)
				itemset.addItem(i);
		return itemset;
	}
}
