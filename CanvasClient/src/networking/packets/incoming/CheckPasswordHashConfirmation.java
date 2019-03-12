package networking.packets.incoming;

import java.io.Serializable;

public class CheckPasswordHashConfirmation implements Serializable{
        private static final long serialVersionUID = 1L;
        public int playerSession;
        public String password;
        public String passwordHash;
}
