package fan.frozen.inventoryuimanager.inventory.constants;

/**
 * the preset type of the UI
 * this can be extended to make your own UIType
 * now only store the name of the UIType and only in english
 * <p>in further version will make localization to support multiple languages</p>
 */
public enum UIType {
    SINGLE_PAGE_INVENTORY(){
        @Override
        public String getType() {
            return "SinglePageInventory";
        }
    },
    MULTI_PAGE_INVENTORY(){
        @Override
        public String getType() {
            return "MultiPageInventory";
        }
    },
    NODE_INVENTORY(){
        @Override
        public String getType() {
            return "NodeInventory";
        }
    },
    ANVIL_INVENTORY(){
        @Override
        public String getType() {
            return "AnvilInventory";
        }
    };
    public abstract String getType();
}
