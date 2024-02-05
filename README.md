# StudyCRM by Code Differently

[![Open in Dev Containers](https://img.shields.io/static/v1?label=Dev%20Containers&message=Open&color=blue&logo=visualstudiocode)](https://vscode.dev/redirect?url=vscode://ms-vscode-remote.remote-containers/cloneInVolume?url=https://github.com/anthonydmays/study-crm)

## Build instructions

### Easy way (using VS Code)

1. Install VS Code and Docker. You can references the [instructions here][dev-container-instructions], if needed.
1. Click the `Dev Containers` button above to automatically clone and open the project in a new dev container.
1. Once the dev image is done loading projects, open a new VS Code terminal, run `gradle start` and use the Docker view in VS Code to see all the running containers. When done, you can shut everything down using `gradle teardown`.
1. To run all tests, use `gradle test`.
1. [Configure your HOSTS file][hosts-configuration] to add entries for `local.studycrm.com` and `api-local.studycrm.com` pointing to `127.0.0.1`.
1. Profit!

### Less easy way (using VS Code)

1. Install VS Code and Docker. You can references the [instructions here][dev-container-instructions], if needed.
1. Clone this repo to your local machine

```console
mkdir source
git clone https://github.com/anthonydmays/study-crm 
```

1. Open the new folder you just downloaded in VS Code
1. Follow the prompts to automatically repoen the folder as a dev container.
1. Once the dev image is done loading projects, open a new VS Code terminal, run `gradle start` and use the Docker view in VS Code to see all the running containers. When done, you can shut everything down using `gradle teardown`.
1. To run all tests, use `gradle test`.
1. [Configure your HOSTS file][hosts-configuration] to add entries for `local.studycrm.com` and `api-local.studycrm.com` pointing to `127.0.0.1`.
1. Profit!

### Least easiest way (suitable if using another coding editor)

1. Install VS Code and Docker. You can references the [instructions here][dev-container-instructions], if needed.
1. Install Java 17 and Gradle.
1. Clone this repo to your local machine

```console
mkdir source
git clone https://github.com/anthonydmays/study-crm 
cd study-crm 
```

1. You can now open the project in your favorite coding editor. 
1. To start all the services, run `gradle start` in the `study-crm` directory. You should see all the containers running in Docker. When done, you can shut everything down using `gradle teardown`.
1. To run all tests, use `gradle test`.
1. [Configure your HOSTS file][hosts-configuration] to add entries for `local.studycrm.com` and `api-local.studycrm.com` pointing to `127.0.0.1`.
1. Profit!


[dev-container-instructions]: https://aka.ms/vscode-remote/containers/getting-started
[hosts-configuration]: https://www.hostinger.com/tutorials/how-to-edit-hosts-file