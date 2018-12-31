package tech.arinzedroid.dspeech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslateResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     *
     */
    public TranslateResponse() {
    }

    /**
     *
     * @param data
     */
    public TranslateResponse(Data data) {
        super();
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("translations")
        @Expose
        private List<Translation> translations = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Data() {
        }

        /**
         *
         * @param translations
         */
        public Data(List<Translation> translations) {
            super();
            this.translations = translations;
        }

        public List<Translation> getTranslations() {
            return translations;
        }

        public void setTranslations(List<Translation> translations) {
            this.translations = translations;
        }

    }

    public class Translation {

        @SerializedName("detectedSourceLanguage")
        @Expose
        private String detectedSourceLanguage;
        @SerializedName("translatedText")
        @Expose
        private String translatedText;

        /**
         * No args constructor for use in serialization
         *
         */
        public Translation() {
        }

        /**
         *
         * @param detectedSourceLanguage
         * @param translatedText
         */
        public Translation(String detectedSourceLanguage, String translatedText) {
            super();
            this.detectedSourceLanguage = detectedSourceLanguage;
            this.translatedText = translatedText;
        }

        public String getDetectedSourceLanguage() {
            return detectedSourceLanguage;
        }

        public void setDetectedSourceLanguage(String detectedSourceLanguage) {
            this.detectedSourceLanguage = detectedSourceLanguage;
        }

        public String getTranslatedText() {
            return translatedText;
        }

        public void setTranslatedText(String translatedText) {
            this.translatedText = translatedText;
        }

    }

}