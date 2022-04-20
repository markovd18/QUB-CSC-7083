package uk.qub.se.board.area;

public enum AreaStage {
    MAJOR_DEVELOPMENT_STAGE(null),
    DEVELOPED_3(MAJOR_DEVELOPMENT_STAGE),
    DEVELOPED_2(DEVELOPED_3),
    DEVELOPED_1(DEVELOPED_2),
    DEVELOPED_0(DEVELOPED_1),
    NOT_OWNED(DEVELOPED_0);

    private final AreaStage nextStage;

    AreaStage(AreaStage nextStage) {
        this.nextStage = nextStage;
    }

    public boolean isHighestStage(){
        return this.nextStage == null;
    }

    public AreaStage getNextStage() {
        return nextStage;
    }

    public boolean isNextStageTheHighest() {
        return this.nextStage != null && this.nextStage.nextStage == null;
    }
}
