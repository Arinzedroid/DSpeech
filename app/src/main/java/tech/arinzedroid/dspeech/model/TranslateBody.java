package tech.arinzedroid.dspeech.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslateBody {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("model")
    @Expose
    private String model;

    /**
     * No args constructor for use in serialization
     *
     */
    public TranslateBody() {
    }

    /**
     *
     * @param model
     * @param source
     * @param q
     * @param target
     * @param format
     */
    public TranslateBody(String q, String target, String format, String source, String model) {
        super();
        this.q = q;
        this.target = target;
        this.format = format;
        this.source = source;
        this.model = model;

    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
