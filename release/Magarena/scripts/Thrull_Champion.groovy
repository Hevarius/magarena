def THRULL=new MagicPermanentFilterImpl() {
    public boolean accept(final MagicGame game,final MagicPlayer player,final MagicPermanent target) {
        return target.isCreature() && target.hasSubType(MagicSubType.Thrull);
    } 
};

def TARGET_THRULL = new MagicTargetChoice(
    THRULL,
    "target thrull"
);

[
    new MagicPermanentActivation(
        new MagicActivationHints(MagicTiming.Removal),
        "Control"
    ) {

        @Override
        public Iterable<MagicEvent> getCostEvent(final MagicPermanent source) {
            return [
                new MagicTapEvent(source)
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source, final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                TARGET_THRULL,
                MagicExileTargetPicker.create(),
                this,
                "Gain control of target Thrull\$ for as long as PN controls SN."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                game.doAction(new MagicAddStaticAction(
                    event.getPermanent(), 
                    MagicStatic.ControlAsLongAsYouControlSource(
                        event.getPlayer(),
                        it
                    )
                ));
            });
        }
    }
]
