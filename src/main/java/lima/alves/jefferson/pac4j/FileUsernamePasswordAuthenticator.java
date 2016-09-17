package lima.alves.jefferson.pac4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.UsernamePasswordAuthenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;

public class FileUsernamePasswordAuthenticator 
       implements UsernamePasswordAuthenticator{

    private Path filePath;
    
    public FileUsernamePasswordAuthenticator(final String file){
        
        this.filePath = Paths.get(file);
        
        if(!Files.isReadable(this.filePath)){
            throw new IllegalArgumentException("Couldn't read the file");
        }
        
    }
    
    public void validate(UsernamePasswordCredentials credentials,
                         WebContext context) throws HttpAction {
        
        final String username = credentials.getUsername();
        final String password = credentials.getPassword();
        
        try(BufferedReader reader = 
                new BufferedReader(new FileReader(this.filePath.toFile()))){
            
            String line = null;
            
            while((line = reader.readLine()) != null){
                                
                final String[] lineSplit = line.split(",");
                
                final String storedUsername = lineSplit[0].trim();
                final String storedPassword = lineSplit[1].trim();
                
                if(username.equals(storedUsername) &&
                   password.equals(storedPassword)){
                    
                    final CommonProfile profile = new CommonProfile();
                    profile.setId(username);
                    profile.addAttribute(Pac4jConstants.USERNAME, username);
                    credentials.setUserProfile(profile);
                    
                    return;
                    
                }
            }
            
            throw new CredentialsException("Username or password invalid");
            
        }catch(IOException e){
            throw new RuntimeException("Error while trying to retrieve the credentials ");
        }
        
    }

    
    
}
