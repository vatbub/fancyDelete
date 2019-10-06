# FancyDelete
> Delete files - but with style

Ever wanted to delete files with the same file name but different extensions?
My camera stores RAW and JPG images side by side with the same file name, but obviously with different file extensions.
Previously, when I had a photo that I wanted to delete, I had to delete both copies separately. 
Now, I assigned a single keyboard shortcut in my photo management tool which sends the file to fancyDelete which deletes all related files for me.

## Features
- User passes a file name and fancyDelete deletes all files in that folder with the same file name but different file extensions
- Simple command line interface
- Simple to use while giving you enough control over how your files are deleted

## Download
- [GitHub Releases](https://github.com/vatbub/fancyDelete/releases)
- or [Bintray](https://bintray.com/vatbub/fokprojectsReleases/fancyDelete#downloads)

## Prerequisites
You need Java 8 for this program to work.

## Usage
It's so simple:
```cmd
fancyDelete-1.0-WindowsExecutable.exe "C:\Users\account\Pictures\myPicture.png"
```

By default, this will do the following:

- Ask you for confirmation before any file is deleted
- Delete all files that are called `myPicture` and are located in the folder `C:\Users\account\Pictures\`, e. g.:
    - `C:\Users\account\Pictures\myPicture.png`
    - `C:\Users\account\Pictures\myPicture.dwg`
    - `C:\Users\account\Pictures\myPicture.jpg`
    - `C:\Users\account\Pictures\myPicture.RAW`
    
> I personally recommend you to add a shortcut in your photo management tool to easily access fancyDelete. My tool (XnViewMP) has a menu "Open with..." where I can just configure it to open the file with fancyDelete. This way, I only have to hit one keyboard shortcut to delete all related photos.

### Additional options and configuration
If you want to delete only files with a specific extension, specify a list of extensions using
```cmd
fancyDelete-1.0-WindowsExecutable.exe "C:\Users\account\Pictures\myPicture.png" --extensions png txt
```
In that case, only `myPicture.png` and `myPicture.txt` would be deleted whereas `myPicture.dng` would be left alone.

If you want to see what the program would do without causing any harm, enable dry run mode:
```cmd
fancyDelete-1.0-WindowsExecutable.exe --dryRun true
```

Disable dry run again using
```cmd
fancyDelete-1.0-WindowsExecutable.exe --dryRun false
```

If you wish to suppress the confirmation before a file is deleted once, add the `-y` parameter:
```cmd
fancyDelete-1.0-WindowsExecutable.exe "C:\Users\account\Pictures\myPicture.png" -y
```

If you wish to disable the confirmation before a file is deleted completely, use the following:
```cmd
fancyDelete-1.0-WindowsExecutable.exe --confirmation false
```
Re-enable the confirmation again using
```cmd
fancyDelete-1.0-WindowsExecutable.exe --confirmation true
```

To display a general help message, type this:
```cmd
fancyDelete-1.0-WindowsExecutable.exe -h
```

## Building
This project is written in Kotlin and built using Maven. To build this project, you therefore need:
- Java JDK 8
- Maven 3

1. Clone the repository and `cd`into it
2. Run `mvn package`
3. Maven will create a subfolder called `target` in which it will store all build outputs.

# Icon
<div>Icons made by <a href="https://www.flaticon.com/authors/google" title="Google">Google</a> from <a href="https://www.flaticon.com/"             title="Flaticon">www.flaticon.com</a></div>
