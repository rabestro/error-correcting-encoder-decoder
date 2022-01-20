/**
 * Error Correcting Encoder-Decoder
 */
module correcter.main {
    exports lv.id.jc.correcter;
    opens lv.id.jc.correcter.config to spring.core, spring.beans, spring.context;

    requires spring.context;
    requires spring.core;
    requires spring.beans;
}

