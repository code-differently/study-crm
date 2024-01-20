find . -type f \( -name "Dockerfile" -o -name "*.yaml" -o -name "*.properties" -o -name "*.kts" -o -name "*.java" -o -name "*.yaml" -o -name ".classpath" -o -name ".project" \) -not \( -path "./bin/*" -o -path "./build/*" \) -exec sed -i '' -e 's/simple-crm/study-crm/g; s/simplecrm/studycrm/g' {} +

find . -type f \( -name "Dockerfile" -o -name "*.yaml" -o -name "*.properties" -o -name "*.kts" -o -name "*.java" -o -name "*.yaml" -o -name ".classpath" -o -name ".project" \) -not \( -path "./bin/*" -o -path "./build/*" \) -exec sed -i '' -e 's/org.studycrm/org.codedifferently.studycrm/g' {} +

find . -type d  -not \( -path "./bin/*" -o -path "./build/*" \) -name "simplecrm" -execdir mv {} codedifferently/studycrm \;
  