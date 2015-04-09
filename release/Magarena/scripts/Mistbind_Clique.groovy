[
    new MagicTrigger<MagicExileLinkAction>() {
        @Override
        public MagicTriggerType getType() {
            return MagicTriggerType.WhenChampioned;
        }
         
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicExileLinkAction action) {
            return action.source == permanent && action.permanent.hasSubType(MagicSubType.Faerie) ?
                new MagicEvent(
                    permanent,
                    NEG_TARGET_PLAYER,
                    this,
                    "Tap all lands target player\$ controls."
                ):
                MagicEvent.NONE;
        }

         @Override
         public void executeEvent(final MagicGame game, final MagicEvent event) {
             event.processTargetPlayer(game, {
                LAND_YOU_CONTROL
                .filter(game, it) each {
                    final MagicPermanent land ->
                    game.doAction(new MagicTapAction(land));
                }
             });
        }
    }
]
