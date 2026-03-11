$classpath = "target\classes"
Get-ChildItem -Path "target\dependency" -Name "*.jar" | ForEach-Object {
    $classpath += ";target\dependency\$_"
}

Write-Host "Classpath: $classpath"
java -cp "$classpath" org.example.studentApplication