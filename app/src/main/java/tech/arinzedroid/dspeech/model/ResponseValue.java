package tech.arinzedroid.dspeech.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseValue {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseValue() {
    }

    /**
     *
     * @param results
     */
    public ResponseValue(List<Result> results) {
        super();
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {

        @SerializedName("alternatives")
        @Expose
        private List<Alternative> alternatives = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Result() {
        }

        /**
         *
         * @param alternatives
         */
        public Result(List<Alternative> alternatives) {
            super();
            this.alternatives = alternatives;
        }

        public List<Alternative> getAlternatives() {
            return alternatives;
        }

        public void setAlternatives(List<Alternative> alternatives) {
            this.alternatives = alternatives;
        }

       public static class Alternative {

            @SerializedName("transcript")
            @Expose
            private String transcript;
            @SerializedName("confidence")
            @Expose
            private Double confidence;

            /**
             * No args constructor for use in serialization
             *
             */
            public Alternative() {
            }

            /**
             *
             * @param transcript
             * @param confidence
             */
            public Alternative(String transcript, Double confidence) {
                super();
                this.transcript = transcript;
                this.confidence = confidence;
            }

            public String getTranscript() {
                return transcript;
            }

            public void setTranscript(String transcript) {
                this.transcript = transcript;
            }

            public Double getConfidence() {
                return confidence;
            }

            public void setConfidence(Double confidence) {
                this.confidence = confidence;
            }

        }

    }

}