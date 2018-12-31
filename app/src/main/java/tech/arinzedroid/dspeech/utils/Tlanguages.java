package tech.arinzedroid.dspeech.utils;

public enum Tlanguages {

    GERMAN {
        @Override
        public String languageCode() {
            return "de";
        }
    }, ENGLISH {
        @Override
        public String languageCode() {
            return "en";
        }
    }, SPANISH {
        @Override
        public String languageCode() {
            return "es";
        }
    }, FRENCH {
        @Override
        public String languageCode() {
            return "fr";
        }
    }, ITALIAN {
        @Override
        public String languageCode() {
            return "it";
        }
    }, HAUSA {
        @Override
        public String languageCode() {
            return "ha";
        }
    }, YORUBA {
        @Override
        public String languageCode() {
            return "yo";
        }
    }, IGBO {
        @Override
        public String languageCode() {
            return "ig";
        }
    };

    public abstract String languageCode();
}
