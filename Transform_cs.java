import net.sf.saxon.Configuration;
import net.sf.saxon.Transform;
import net.sf.saxon.s9api.Processor;


public class Transform_cs extends Transform{

    @Override
    public void initializeConfiguration(Configuration config) {
        this.processor = new Processor(config);
    }

}
