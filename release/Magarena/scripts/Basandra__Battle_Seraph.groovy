[
    new MagicStatic(MagicLayer.Player) {
        @Override
        public void modPlayer(final MagicPermanent source, final MagicPlayer player) {
            player.setState(MagicPlayerState.CantCastSpells);
        }
        @Override
        public boolean condition(final MagicGame game,final MagicPermanent source,final MagicPermanent target) {
            return game.isCombatPhase();
        }
    },
    new MagicPermanentActivation( 
        new MagicActivationHints(MagicTiming.MustAttack),
        "Attacks"
    ) {
        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return [
                new MagicPayManaCostEvent(source, "{R}")
            ];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.NEG_TARGET_CREATURE,
                MagicMustAttackTargetPicker.create(),
                this,
                "Target creature\$ attacks this turn if able."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game,new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    game.doAction(new MagicSetAbilityAction(
                        creature,
                        MagicAbility.AttacksEachTurnIfAble
                    ));
                }
            });
        }
    }
]
