package de.united.azubiware.Connection;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PortManager {

    public static final PortManager ports = new PortManager(13001, 14000);

    private final int minPort;
    private final int maxPort;

    private List<Port> freePorts;

    public PortManager(int minPort, int maxPort) {
        this.minPort = minPort;
        this.maxPort = maxPort;

        freePorts = new LinkedList<>();
        for(int i = minPort; i <= maxPort; i++){
            freePorts.add(new Port(i));
        }
    }

    public int getFreePort() throws NoFreePortException{
        for(Port port : freePorts){
            if(port.use()){
                return port.port;
            }
        }
        return -1;
    }

    public void freePort(int port){
        for(Port p : freePorts){
            if(p.port == port) {
                p.free();
                return;
            }
        }
        throw new RuntimeException("Port wasn't occupied!");
    }

    private class Port{
        private final int port;
        private AtomicBoolean free;

        private Port(int port) {
            this.port = port;
            free = new AtomicBoolean();
        }

        public boolean use(){
            return free.compareAndSet(false, true);
        }

        public void free(){
            free.set(false);
        }

    }

    public class NoFreePortException extends Exception{
    }

}
