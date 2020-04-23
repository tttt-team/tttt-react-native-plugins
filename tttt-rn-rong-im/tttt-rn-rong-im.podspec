require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "tttt-rn-rong-im"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  tttt-rn-rong-im
                   DESC
  s.homepage     = "https://github.com/github_account/tttt-rn-rong-im"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Your Name" => "yourname@email.com" }
  s.platforms    = { :ios => "9.0", :tvos => "10.0" }
  s.source       = { :git => "https://github.com/github_account/tttt-rn-rong-im.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
	
  # s.dependency "..."
end

