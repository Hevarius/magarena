def LandYouControl = new MagicTargetChoice("a land you control");
[
    new MagicAdditionalCost() {
        @Override
        public MagicEvent getEvent(final MagicSource source) {
            return new MagicBounceChosenPermanentEvent(source, LandYouControl);
        }
    },

    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_SPELL,
                this,
                "Counter target spell\$"
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetCardOnStack(game, {
                final MagicCardOnStack targetSpell ->
                game.doAction(new MagicCounterItemOnStackAction(targetSpell));
            });
        }
    }
]
