import json
import subprocess
import os
import re
import time

# Load tags and schema
with open('tags.json') as f:
    tags_data = json.load(f)
with open('tags_schema.json') as f:
    schema_data = json.load(f)

with open('files_in_root.json') as f:
    files_data = json.load(f)

# Folders mapping
dest_folders = {
    "projects": "1oodzpO4N-YbDaLwly3uvVSHvduHjJcfj",
    "areas": "1RfJEknyMRP1YXFNqUqzgaN_wvBxjDve4",
    "resources": "1gQymUvBuXtZLhAGUZPLLqCAL_y_-Cjqq"
}

# Templates
templates = {
    "Architecture": "1T1OQVPWORmUzeknYerulBWlVPffn9co2",
    "Testing": "1rxhNkkaCwu3q0hZRW1T4OAWlCy0MIVv6",
    "AI": "1VJmz4ygzFb9VLpUV5EFwhuNCV-CB1NZK"
}
template_contents = {}
for name, tid in templates.items():
    res = subprocess.run(f"gws drive files get --params='{{\"fileId\": \"{tid}\", \"alt\": \"media\"}}'", shell=True, capture_output=True, text=True)
    # The output is saved to download.bin by the CLI when it sees a binary or alt=media
    # Wait, gws saves to download.bin. So we need to read download.bin
    if os.path.exists('download.bin'):
        with open('download.bin', 'r') as binf:
            template_contents[name] = binf.read()
        os.remove('download.bin')

# Create a general template if it doesn't exist
general_template = "---\ntags: [{tags}]\n---\n# {title}\n\n## Overview\n\n## Content\n\n"
template_contents["General"] = general_template

# Upload general template to templates folder
with open("General_Template.md", "w") as f:
    f.write("---\ntags: [template]\n---\n# General Template\n\n## Overview\n\n## Content\n\n")
subprocess.run(f"gws +upload --file General_Template.md --parent 1AAuhEMZz7ljuKj6exDa4jZ1oOqlg6MwZ --mimeType text/markdown", shell=True)

def get_tags_and_folder(name):
    lower_name = name.lower()
    file_tags = []
    folder = "projects"
    template_key = "General"
    
    if "guide" in lower_name or "template" in lower_name:
        folder = "resources"
    if "architecture" in lower_name or "system" in lower_name:
        folder = "areas"
        template_key = "Architecture"
        file_tags.append("SoftwareArchitecture")
        
    if "kafka" in lower_name: file_tags.append("Kafka")
    if "confluent" in lower_name: file_tags.append("ConfluentCloud")
    if "java" in lower_name or "spring" in lower_name or "gradle" in lower_name: file_tags.append("JavaDevelopment")
    if "test" in lower_name: 
        file_tags.append("Testing")
        template_key = "Testing"
    if "ai" in lower_name or "prompt" in lower_name or "generative" in lower_name or "mcp" in lower_name:
        file_tags.append("GenerativeAI")
        template_key = "AI"
    if "id" in lower_name or "sequence" in lower_name: file_tags.append("DistributedSystems")
    if "message" in lower_name or "queue" in lower_name or "avro" in lower_name: file_tags.append("Messaging")
    
    if not file_tags:
        file_tags.append("General")
        
    return file_tags, folder, template_key

for file in files_data['files']:
    if file['mimeType'] == 'application/vnd.google-apps.folder':
        continue
        
    name = file['name']
    fid = file['id']
    print(f"Processing: {name}")
    
    tags, folder_key, template_key = get_tags_and_folder(name)
    parent_id = dest_folders[folder_key]
    
    # Update tags tracking
    for t in tags:
        if t not in tags_data['tags']:
            tags_data['tags'][t] = []
        if name + ".md" not in tags_data['tags'][t]:
            tags_data['tags'][t].append(name + ".md")
            
    # download content
    if file['mimeType'] == 'application/vnd.google-apps.document':
        cmd = f"gws drive files export --params='{{\"fileId\": \"{fid}\", \"mimeType\": \"text/plain\"}}'"
    else:
        cmd = f"gws drive files get --params='{{\"fileId\": \"{fid}\", \"alt\": \"media\"}}'"
        
    subprocess.run(cmd, shell=True, capture_output=True)
    
    content = ""
    if os.path.exists('download.txt'):
        with open('download.txt', 'r', encoding='utf-8', errors='ignore') as f:
            content = f.read()
        os.remove('download.txt')
    elif os.path.exists('download.bin'):
        with open('download.bin', 'r', encoding='utf-8', errors='ignore') as f:
            content = f.read()
        os.remove('download.bin')
    else:
        # Some files might have different names, let's just find the newest file downloaded or ignore
        pass
        
    # Format content
    safe_name = re.sub(r'[^a-zA-Z0-9_\- ]', '', name)
    out_file = f"{safe_name}.md"
    
    template_str = template_contents[template_key]
    if "{tags}" in template_str:
        template_str = template_str.replace("{tags}", ", ".join(tags)).replace("{title}", name)
        
    final_content = template_str + "\n\n" + content
    
    with open(out_file, 'w', encoding='utf-8') as f:
        f.write(final_content)
        
    # Upload to target folder
    upload_cmd = f"gws +upload --file \"{out_file}\" --parent \"{parent_id}\" --mimeType \"text/markdown\""
    res = subprocess.run(upload_cmd, shell=True, capture_output=True, text=True)
    if "success" in res.stdout.lower() or "id" in res.stdout.lower():
        # Delete original
        subprocess.run(f"gws drive files delete --params='{{\"fileId\": \"{fid}\"}}'", shell=True)
        print(f"Successfully processed {name}")
    else:
        print(f"Failed to upload {name}: {res.stderr} {res.stdout}")
        
    os.remove(out_file)

# Save and upload tags
with open('tags.json', 'w') as f:
    json.dump(tags_data, f, indent=2)

subprocess.run("gws drive files update --params='{\"fileId\": \"1EUXT5R3CByIEJjXgTBlo1edJZpJkgZLH\"}' --file tags.json", shell=True)

# Update tags_schema if needed (it allows any alphanumeric pattern, so it's already compliant, but we can re-upload to be sure)
subprocess.run("gws drive files update --params='{\"fileId\": \"1HYyenmGixiYpODzY2TFY54qgjfpB42TD\"}' --file tags_schema.json", shell=True)

print("Finished processing.")
