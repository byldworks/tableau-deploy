![Java CI with Maven](https://github.com/byldworks/tableau-deploy/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

# tableau-deploy
A tool to publish Tableau dashboards to Tableau server.

## Notes and Gothcas

Adding Tableau to your CI/CD pipeline comes with a number of unexpected surprises and gotchas. Below is a list (or perhaps more of a brain-dump) on some items to keep in mind when developing a Tableau deployment solution:

* In order to check in your workbook to Git (or another VCS) you need to ensure that your embedded extract extracts are empty. For reference: https://www.tableau.com/en-gb/about/blog/2013/9/easy-empty-local-extracts-25152-0 

* It is important to set the appropriate owner for the workbook. This will ensure notifications will go to the appropriate person / group.

* Ensure you force UTF-8 encoding everywhere when dealing with the XML file. Mistakes can creep in if you work on both Windows and Linux. Some versions of these will have default character sets not supported by Tableau. Using UTF-8 is always the safest.

* You may find that setting refresh schedules on your workbook through the API might inexplicably fail. This is because there is a required ordering to how `datasource` children elements should appear in the `datasources`parent. You *must* ensure that all `datasource` children elements with a `refresh` sub-child with an `incremental-updates` attribute of `true` must be last in that child list.
 
## Phased Deployment Workflow

When publishing a Tableau workbook it is often desirable to keep user downtime to a minimum. This can be accomplished by first publishing the new workbook as a candidate. Following user or automated test verification it can then be 'switched' over. To accomplish this you generally follow these steps:

1) Publish the workbook with a 'candidate' name (e.g. "*___Candidate"). API call `workbook?overwrite=true&asJob=true`
2) Await the completion of the job. API call `jobs/jobId`
3) Update workbook owner and workbook connections (the latter needs to be done if you update the owner)
4) Refresh the 'candidate' workbook (remember up to this point the workbook had an empty extract)
5) Perform verification
6) Download full workbook. API call `/workbooks/<workbook_id>/content?includeExtract=true`
7) Update the *.twb file inside the *.twbx file if required (e.g. reorder the `datasource` elements as described above)
8) Upload the new workbook with the corrected name (e.g. drop the "*___Candidate" suffix)

While this workflow is more involved, it reduces the downtime that any user will experience. Especially if step (4) takes a long time to complete.


