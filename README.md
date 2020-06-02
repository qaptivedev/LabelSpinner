# LabelSpinner
Spinner with InputTextLayout style hint.Usage is almost same as the spinner.
# Usage 
## Add dependencies

Currently the GitHub Packages requires us to Authenticate to download an Android Library (Public or Private) hosted on the GitHub Package. This might change for future releases.

## Step 1 : Generate a Personal Access Token for GitHub
    Inside you GitHub account:
    Settings -> Developer Settings -> Personal Access Tokens -> Generate new token
    Make sure you select the following scopes (“ read:packages”) and Generate a token
    After Generating make sure to copy your new personal access token. You cannot see it again! The only option is to generate a new key.

Crate file named github.properties(feel free to change file name)
and add the following
```
gpr.usr=github_user_id
gpr.key=github_Personal_access_tokens
```
In Project build.gradle
```
def githubProperties = new Properties()
githubProperties.load(new FileInputStream(rootProject.file('github.properties')))

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            name = "LabelSpinner"
            url = uri("https://maven.pkg.github.com/qaptivedev/LabelSpinner")
            credentials {
                /** Create github.properties in root project folder file with
                 ** gpr.usr=GITHUB_USER_ID & gpr.key=PERSONAL_ACCESS_TOKEN
                 ** Or set env variable GPR_USER & GPR_API_KEY if not adding a properties file**/

                username = githubProperties['gpr.usr'] ?: System.getenv("GPR_USER")
                password = githubProperties['gpr.key'] ?: System.getenv("GPR_API_KEY")
            }
        }
        
    }
}
```
In App build.gradle add
```
dependencies {
    ......
    ........
    implementation 'com.qaptive.labelspinnerlibrary:labelspinnerlibrary:1.0.0-beta-1'
    }
```
## With outline
```
<com.qaptive.labelspinnerlibrary.LabelSpinner
        android:id="@+id/label_spinner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:spinnerMode="dropdown"
        app:label="DropDown"
        app:labelType="outline"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@+id/guideline"/>
```
## With Floating Label
```

    <com.qaptive.labelspinnerlibrary.LabelSpinner
        android:id="@+id/spinner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:spinnerMode="dialog"
        app:label="Dialog"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/guideline"
        app:layout_constraintBottom_toBottomOf="parent"/>
```
##Set Adapter
Adapter need to implement LabelBaseAdapter and must be sub class of BaseAdapter.
```
val adapter=LabelArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item)
        adapter.add("Hello")
        adapter.add("you")
        adapter.add("TERF")
        spinner.setAdapter(adapter)
        
spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity,"onNothingSelected",Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity,"position:$position, id:$id",Toast.LENGTH_SHORT).show()
            }

        }
        )
```
## Styling
Override
* *For Outline*
```
LabelSpinner.Outline
```

* *For Floating*
```
LabelSpinner.FilledBox
```
