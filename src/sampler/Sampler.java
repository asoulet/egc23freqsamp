package sampler;


import java.util.AbstractCollection;

public interface Sampler {

	public int getK();

	public AbstractCollection<Pattern> getSample();

}
