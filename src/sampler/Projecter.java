package sampler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Projecter {

	private Dataset dataset;
	private Projection[] projections;

	public Projecter(Dataset dataset) {
		this.dataset = dataset;
		projections = new Projection [dataset.getItemNumber() + 1];
		for (int p = 1; p < projections.length; p++)
			projections[p] = new Projection(dataset.getTransactionNumber(), dataset.getItemNumber());
		project();
	}

	private void project() {
		for (int t = 0; t < dataset.getTransactionNumber(); t++) {
			ArrayList<Integer> transaction = new ArrayList<Integer>();
			for (int i = 1; i <= dataset.getItemNumber(); i++)
				if (dataset.getMatrix()[t][i])
					transaction.add(i);
			Collections.sort(transaction, new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					return dataset.getItemFrequency(o1) - dataset.getItemFrequency(o2);
				}
			});
			for (int i = 0; i < transaction.size(); i++)
				projections[transaction.get(i)].add(transaction, i);
		}
	}
	
	public double getWeight() {
		double w = 0;
		for (int p = 1; p < projections.length; p++) {
			double v = projections[p].getWeight();
			w += v;
		}
		return w;
	}

	public void reduce(int absolute) {
		for (int p = 1; p < projections.length; p++)
			projections[p].reduce(absolute);
	}

	public int getSize() {
		int s = 0;
		for (int p = 1; p < projections.length; p++) {
			int v = projections[p].getSize();
			s += v;
		}
		return s;
	}
}
