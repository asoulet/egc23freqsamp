package sampler;

import java.util.ArrayList;

public class Projection {

	private int transactionNumber;
	private int itemNumber;
	private ArrayList<ArrayList<Integer>> transactions = new ArrayList<ArrayList<Integer>>();
	private int [] itemFrequency;

	public Projection(int transactionNumber, int itemNumber) {
		this.transactionNumber = transactionNumber;
		this.itemNumber = itemNumber;
		itemFrequency = new int [itemNumber + 1];
	}
	
	public void add(ArrayList<Integer> transaction, int index) {
		ArrayList<Integer> nt = new ArrayList<Integer>();
		for (int i = index + 1; i < transaction.size(); i++)
			if (i != index) {
				int item = transaction.get(i);
				nt.add(item);
				itemFrequency[item]++;
			}
		transactions.add(nt);
	}
	
	public void reduce(int threshold) {
		for (ArrayList<Integer> t : transactions)
			for (int i = t.size() - 1; i >=0; i--)
				if (itemFrequency[t.get(i)] < threshold)
					t.remove(i);			
	}
	
	public double getWeight() {
		double w = 0;
		for (ArrayList<Integer> t : transactions) {
			w += Math.pow(2, t.size());
		}
		return w;
	}

	public int getSize() {
		int s = 0;
		for (ArrayList<Integer> t : transactions) {
			s += t.size();
		}
		return s;
	}
}
