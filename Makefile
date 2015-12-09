
create:
	jekyll new .new
	cp -R .new/* ./
	rm -rf .new/

serve:
	jekyll serve --host=0.0.0.0 --force_polling
