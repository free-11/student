$classpath = "target\classes"
Get-ChildItem -Path "target\dependency" -Name "*.jar" | ForEach-Object {
    $classpath += ";target\dependency\$_"
}

java -cp "$classpath" org.example.studentApplication