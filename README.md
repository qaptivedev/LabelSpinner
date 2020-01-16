# LabelSpinner
Spinner with InputTextLayout style hint.Usage is almost same as the spinner.
# Usage 
## With outline
```
<com.srl.labelspinnerlibrary.LabelSpinner
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

    <com.srl.labelspinnerlibrary.LabelSpinner
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
```
val adapter=ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item)
        adapter.add("Hello")
        adapter.add("you")
        adapter.add("TERF")
        spinner.setAdapter(adapter)
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
