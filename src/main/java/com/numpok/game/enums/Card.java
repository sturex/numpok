package com.numpok.game.enums;

public enum Card {
    ACE {
        @Override
        public Character asChar() {
            return 'A';
        }
    }, KING {
        @Override
        public Character asChar() {
            return 'K';
        }
    }, QUEEN {
        @Override
        public Character asChar() {
            return 'Q';
        }
    }, JACK {
        @Override
        public Character asChar() {
            return 'J';
        }
    }, TEN {
        @Override
        public Character asChar() {
            return 'T';
        }
    }, NINE {
        @Override
        public Character asChar() {
            return '9';
        }
    }, EIGHT {
        @Override
        public Character asChar() {
            return '8';
        }
    }, SEVEN {
        @Override
        public Character asChar() {
            return '7';
        }
    }, SIX {
        @Override
        public Character asChar() {
            return '6';
        }
    }, FIVE {
        @Override
        public Character asChar() {
            return '5';
        }
    }, FOUR {
        @Override
        public Character asChar() {
            return '4';
        }
    }, THREE {
        @Override
        public Character asChar() {
            return '3';
        }
    }, TWO {
        @Override
        public Character asChar() {
            return '2';
        }
    };

    public abstract Character asChar();
}
