package tech.arinzedroid.dspeech.model;

/**
 * Created by ACER on 5/9/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rbody {

    @SerializedName("audio")
    @Expose
    private Audio audio;
    @SerializedName("config")
    @Expose
    private Config config;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rbody() {
    }

    /**
     *
     * @param audio
     * @param config
     */
    public Rbody(Audio audio, Config config) {
        super();
        this.audio = audio;
        this.config = config;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static class Audio {

        @SerializedName("content")
        @Expose
        private String content;

        /**
         * No args constructor for use in serialization
         *
         */
        public Audio() {
        }

        /**
         *
         * @param content
         */
        public Audio(String content) {
            super();
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

    public static class Config {

        @SerializedName("enableAutomaticPunctuation")
        @Expose
        private Boolean enableAutomaticPunctuation = true;
        @SerializedName("encoding")
        @Expose
        private String encoding = "AMR_WB";
        @SerializedName("languageCode")
        @Expose
        private String languageCode = "en-GB";
        @SerializedName("sampleRateHertz")
        @Expose
        private int model = 16000;

        /**
         * No args constructor for use in serialization
         *
         */
        public Config() {
        }

        /**
         *
         * @param languageCode
         * @param encoding
         * @param enableAutomaticPunctuation
         */
        public Config(Boolean enableAutomaticPunctuation, String encoding, String languageCode) {//, String model
            super();
            this.enableAutomaticPunctuation = enableAutomaticPunctuation;
            this.encoding = encoding;
            this.languageCode = languageCode;
            //this.model = model;
        }

        public Boolean getEnableAutomaticPunctuation() {
            return enableAutomaticPunctuation;
        }

        public void setEnableAutomaticPunctuation(Boolean enableAutomaticPunctuation) {
            this.enableAutomaticPunctuation = enableAutomaticPunctuation;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }

        public int getModel() {
            return model;
        }

        public void setModel(int model) {
            this.model = model;
        }

    }

}