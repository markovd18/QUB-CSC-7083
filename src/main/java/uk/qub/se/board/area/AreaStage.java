package uk.qub.se.board.area;

public enum AreaStage {
    DEVELOPED_3(null),
    DEVELOPED_2(DEVELOPED_3),
    DEVELOPED_1(DEVELOPED_2),
    DEVELOPED_0(DEVELOPED_1),
    NOT_OWNED(DEVELOPED_0);

    private final AreaStage nextStage;

    AreaStage(AreaStage nextStage) {
        this.nextStage = nextStage;
    }

    public boolean isHighestStage(){
        return this == DEVELOPED_3;
    }

    public AreaStage getNextStage() {
        return nextStage;
    }
}
