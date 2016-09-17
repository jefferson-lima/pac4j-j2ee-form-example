package lima.alves.jefferson.pac4j;

import org.pac4j.core.authorization.authorizer.Authorizer;
import org.pac4j.core.authorization.authorizer.RequireAnyPermissionAuthorizer;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigFactory;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.indirect.FormClient;

public class ConfigFactoryImp implements ConfigFactory{

	public Config build() {

		final String loginUrl = "http://localhost:8080/pac4j-example/login.jsp";
		final String callbackUrl = "http://localhost:8080/pac4j-example/callback";
		
		final String credentialsFile = this.getClass()
		                                 .getClassLoader()
		                                 .getResource("credentials.txt")
		                                 .getPath();

		final Authenticator<UsernamePasswordCredentials> authenticator;
		authenticator = new FileUsernamePasswordAuthenticator(credentialsFile);
		
		final Authorizer<CommonProfile> authorizer;
        authorizer = new RequireAnyPermissionAuthorizer<CommonProfile>("ROLE_ADMIN");
		
		final FormClient formClient = new FormClient(loginUrl, authenticator);
		
		final Config config = new Config(callbackUrl, formClient);		
		config.addAuthorizer("admin", authorizer);
				
		return config;
		
	}
	
}