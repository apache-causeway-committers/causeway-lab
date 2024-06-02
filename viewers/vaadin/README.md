## Dev Notes

To build the branch and :

a) Build causeway snapshot and install into local mvn repo
```sh
# 1. checkout the causeway core repo / branch v3
git clone https://github.com/apache/causeway.git
git checkout v3
cd causeway

# 2. Build causeway prerequisites
cd causeway/supplemental-model
mvn clean install

# 3. Build the causeway and install 3.0.0-snapshot version into local mvn repo
cd ../..
mvn clean install
```

b) Build causeway lab vaadin viewer and start the app with mvn:
```sh
git clone https://github.com/apache-causeway-committers/causeway-lab.git 
git checkout v3
cd causeway-lab/viewers/vaadin
mvn clean install
(cd demo && mvn spring-boot:run)
```

c) Import in IJ
1. import causeway-lab/viewers/vaadin into intellij and
2. run in the demo module (DemoAppVaadin) and set the working dir to $MODULE_DIR$ OR
3. open http://localhost:8080/ in browser
4. use button login as sven
5. use the employee menu to create a new one or list all