import net.sf.saxon.Configuration;
import net.sf.saxon.lib.LocalizerFactory;
import net.sf.saxon.lib.Numberer;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setLocalizerFactory(new LocalizerFactory() {
            public Numberer getNumberer(String language, String country) {
                if (language.equals("cs")) {
                    return new Numberer_cs();
                }
                return null;
            }
        });
        Transform_cs tr = new Transform_cs();
        tr.initializeConfiguration(config);
        tr.doTransform(args, "");

    }
}
