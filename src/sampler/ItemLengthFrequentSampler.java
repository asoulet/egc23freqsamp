package sampler;

import java.util.ArrayList;

public class ItemLengthFrequentSampler extends FrequentSampler {

	private double weights[] = null;
	private int[][] maximumItemsetLength = null;
	private double[] factoriel = null;
	private int maxlen = 0;

	public ItemLengthFrequentSampler(Dataset dataset, int k, float threshold) {
		super(dataset, k, threshold);
	}

	protected void prepare() {
		dataset.removeNonFrequentItems(threshold);
		maxlen = dataset.getMaximumLength();
		factoriel = new double[maxlen + 2];
		factoriel[0] = 1;
		for (int i = 1; i <= maxlen + 1; i++)
			factoriel[i] = i * factoriel[i - 1];
		computeLocalMaximumLength();
		weights = new double[dataset.getTransactionNumber()];
		for (int t = 0; t < dataset.getTransactionNumber(); t++) {
			double value = 0;
			for (int i = 0; i <= dataset.getTransactionLength(t); i++) if (maximumItemsetLength[t][i] >= i){
				int n = maximumItemsetLength[t][i];
				value += factoriel[n]
						/ (factoriel[i] * factoriel[n - i]);
			}
			total += value;
			weights[t] = total;
		}
	}

	private void computeLocalMaximumLength() {
		int absolute = (int) Math.ceil(threshold * dataset.getTransactionNumber());
		maximumItemsetLength = new int[dataset.getTransactionNumber()][maxlen + 1];
		System.out.println(absolute);
		boolean[][] matrix = dataset.getMatrix();
		int[][][] lengths = new int[dataset.getTransactionNumber()][maxlen + 1][maxlen + 1];
		for (int t = 0; t < dataset.getTransactionNumber(); t++)
			for (int u = t; u < dataset.getTransactionNumber(); u++) {
				ArrayList<Integer> intersectionT = new ArrayList<Integer>();
				ArrayList<Integer> intersectionU = new ArrayList<Integer>();
				int kt = 0;
				int ku = 0;
				for (int i = 1; i < dataset.getItemNumber() + 1; i++) {
					if (matrix[t][i])
						kt++;
					if (matrix[u][i])
						ku++;
					if (matrix[t][i] && matrix[u][i]) {
						intersectionT.add(kt);
						intersectionU.add(ku);
					}

				}
				for (Integer k : intersectionT)
					lengths[t][k][intersectionT.size()]++;
				if (u != t)
					for (Integer k : intersectionU)
						lengths[u][k][intersectionU.size()]++;
			}
		for (int t = 0; t < dataset.getTransactionNumber(); t++) {
			for (int i = 1; i < maxlen + 1; i++) {
				int m = maxlen + 1;
				int sum = 0;
				do {
					m--;
					sum += lengths[t][i][m];
				} while (sum < absolute && m>0);
				for (int j = 0; j <= m; j++)
					maximumItemsetLength[t][j]++;
			}
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
