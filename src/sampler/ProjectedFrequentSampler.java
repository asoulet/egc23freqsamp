package sampler;

public class ProjectedFrequentSampler extends FrequentSampler {
	
	private Projecter projecter;
	
	public ProjectedFrequentSampler(Dataset dataset, int k, float threshold) {
		super(dataset, k, threshold);
	}
	
	protected void prepare() {
		dataset.removeNonFrequentItems(threshold);
		projecter = new Projecter(dataset);
		int absolute = (int) Math.ceil(threshold * dataset.getTransactionNumber());
		projecter.reduce(absolute);
		total = projecter.getWeight() + dataset.getTransactionNumber();
	}

	protected Itemset drawPattern() {
		return null;
	}

	@Override
	public int getSize() {
		return projecter.getSize();
	}
	
	

}
