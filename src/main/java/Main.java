import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.AccountType;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        if (args.length == 0){
            System.out.println("Token argument required");
            return;
        }

        String token = args[0];//"NTcxNjYwNzAxOTgzMTc4NzY0.XMQ_eQ.6lx059_xeP3F_8qO4-onj6jsTbw";
        try {
            JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(token).addEventListener(new Listener());

            builder.buildAsync();
        } catch (LoginException e){
            e.printStackTrace();
        }
    }
}
