package networking.packets.incoming;

import java.io.Serializable;

public class CheckPasswordHashConfirmation implements Serializable{
        private static final long serialVersionUID = 5L;
        public String username;
        public String password;
}
