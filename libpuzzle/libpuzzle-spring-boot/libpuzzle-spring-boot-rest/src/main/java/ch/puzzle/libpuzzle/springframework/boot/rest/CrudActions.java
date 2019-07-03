package ch.puzzle.libpuzzle.springframework.boot.rest;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.Action;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActionsConfiguration;

public class CrudActions<TListAction, TFindAction, TCreateAction, TUpdateAction, TDeleteAction> {

    private Config config;

    public CrudActions() {
        config = new Config();
    }

    public TCreateAction create() {
        return config.createActionFactory.create();
    }

    public TListAction list() {
        return config.listActionFactory.create();
    }

    public TFindAction find() {
        return config.findActionFactory.create();
    }

    public TUpdateAction update() {
        return config.updateActionFactory.create();
    }

    public TDeleteAction delete() {
        return config.deleteActionFactory.create();
    }

    public Config configure() {
        return config;
    }

    public class Config {

        private ActionFactory<TFindAction> findActionFactory = Action::unsupported;
        private ActionFactory<TCreateAction> createActionFactory = Action::unsupported;
        private ActionFactory<TUpdateAction> updateActionFactory = Action::unsupported;
        private ActionFactory<TDeleteAction> deleteActionFactory = Action::unsupported;
        private ActionFactory<TListAction> listActionFactory = Action::unsupported;

        public Config use(CrudActionsConfiguration<TListAction, TFindAction, TCreateAction, TUpdateAction, TDeleteAction> config) {
            config.apply(this);
            return this;
        }

        public Config useFindAction(ActionFactory<TFindAction> findActionFactory) {
            this.findActionFactory = findActionFactory;
            return this;
        }

        public Config useCreateAction(ActionFactory<TCreateAction> createActionFactory) {
            this.createActionFactory = createActionFactory;
            return this;
        }

        public Config useUpdateAction(ActionFactory<TUpdateAction> updateActionFactory) {
            this.updateActionFactory = updateActionFactory;
            return this;
        }

        public Config useDeleteAction(ActionFactory<TDeleteAction> deleteActionFactory) {
            this.deleteActionFactory = deleteActionFactory;
            return this;
        }

        public Config useListAction(ActionFactory<TListAction> listActionFactory) {
            this.listActionFactory = listActionFactory;
            return this;
        }
    }
}
