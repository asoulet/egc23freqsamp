package sampler;

public class SamplerTest {
	
	public static String SEP = "\t";
	public static String path = "../data/bin/"; 
	public static String [] filenames = {
			"abalone.bin",
			"cmc.bin",
			"connect.bin",
			"crx.bin",
			"hypo.bin",
			"iris.bin",
			"mushroom.bin",
			"page.bin",
			"retail.bin",
			"sick.bin",
			"vehicle.bin",
			"waveform.bin",
			"pumsb.bin",
			"chess.bin",
			"austral.bin",						
			"T40I10D100K.bin",
		};	

	public static void main(String[] args) {
		for (String filename : filenames)
		for (int t = 20; t <= 20; t++) {
			Dataset dataset = new Dataset(path + filename);
		float threshold = (20 - t) * 0.05f;
		TwoStepSampler ts = new TwoStepSampler(dataset, 0);
		int size = ts.getSize();
		FrequentSampler naive = new NaiveFrequentSampler(dataset, 0, threshold);
		/*FrequentSampler sampler = new LengthFrequentSampler(dataset, 0, threshold);
		FrequentSampler item = new ItemLengthFrequentSampler(dataset, 0, threshold);*/
		FrequentSampler projected = new ProjectedFrequentSampler(dataset, 0, threshold);
		System.out.print(filename + SEP + threshold + SEP + projected.getAbsolute() + SEP + ts.getTotal() + SEP + naive.getTotal() + SEP + projected.getTotal() + SEP);
		System.out.println(size + SEP + naive.getSize() + SEP + projected.getSize());
		}
	}

}
