package tech.arinzedroid.dspeech.utils;

public enum Languages {
    GERMAN{
        @Override
        public String languageCode(){
            return "de-DE";
        }
    },ENGLISH_NG{
        @Override
        public String languageCode(){
            return "en-NG";
        }
    },ENGLISH_UK{
        @Override
        public String languageCode(){
            return "en-GB";
        }
    },SPANISH{
        @Override
        public String languageCode(){
            return "es-ES";
        }
    },FRENCH{
        @Override
        public String languageCode(){
            return "fr-FR";
        }
    },ITALIAN{
        @Override
        public String languageCode(){
            return "it-IT";
        }
    };

    public abstract String languageCode();
}
