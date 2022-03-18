# Save The Planet



# Notes for development
- suggested IDE is IntelliJ IDEA but feel free to use any IDE you like
- **Used Java version is 17!!**
- **Each feature will be done to separate feature branch**
  - for each feature branch there will be a **Merge Request** created to merge it to the master branch
    - before completion, MR will always be marked as **Draft**
  - each merge request has to be approved by at least one other member of the team before merging into master
  - feature branch commits will always be *squashed* before merge
    - this results in single commit being merged into master and cleaner git tree
    - squashing will only not be done in such cases where it is specifically required and after consultation with other team members during review
- **Merging and rebasing**
  - we do not like merging in general
  - when pulling changes into local branch, always do a *rebase* (with `git pull --rebase`)
  - when merging a feature request, merge is not allowed before the feature branch is rebased on top of master branch
    - this results in merges being just ordinary commits on top of master branch status and cleaner git tree
  - if possible, setup automatic rebasing when fetching changes into your local branch in your IDE
- **Commit messages**
  - for commit messages, let's try to keep them to industry standard format being *Conventional Commits*
  - every message is short and descriptive and starting always with specific keyword
    - *feat:* - new feature being added
    - *fix:* - bug fix being
    - *chore:* - basic chores like changing default configuration etc.
    - *doc:* - changes to the documentation
  - these messages and prefixes are closely tied to *semantic versioning* of a project which will probably not be needed in this project
    - but it still is a nice way to inform teammates about the nature of committed changes and keeping with industry standards
  - for further details visit [**SemVer homepage**](https://semver.org/) and [**Conventional Commits homepage**](https://www.conventionalcommits.org/en/v1.0.0/)
- for any details about development requirements, ask David