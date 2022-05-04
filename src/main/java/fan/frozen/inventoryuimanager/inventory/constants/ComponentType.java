package fan.frozen.inventoryuimanager.inventory.constants;

/**
 * the preset of the component type<br>
 * you can customize your own component type by extend it
 */
public enum ComponentType {
    BUTTON(){
        @Override
        public String getType() {
            return "button";
        }
    },
    LABEL(){
        @Override
        public String getType() {
            return "label";
        }
    },
    BORDER(){
        @Override
        public String getType() {
            return "border";
        }
    },
    SEARCHER(){
        @Override
        public String getType() {
            return "searcher";
        }
    };
    public abstract String getType();
}
