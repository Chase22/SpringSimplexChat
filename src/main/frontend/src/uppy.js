import Uppy from "@uppy/core";
import Dashboard from "@uppy/dashboard";

import '@uppy/core/dist/style.css'
import '@uppy/dashboard/dist/style.css'
import XHRUpload from "@uppy/xhr-upload";

export let uppyInstance;

export function uppy(messageField) {

    uppyInstance = Uppy({
        allowMultipleUploads: false,
        debug: true,
        restrictions: {
            maxFileSize: 3145728,
            maxNumberOfFiles: 1,
            minNumberOfFiles: null,
            allowedFileTypes: [ 'image/*' ],
        },
        meta: {},
    });

    uppyInstance.use(XHRUpload, {
        endpoint: '/api/image',
        formData: true,
        fieldName: 'file',
        getResponseData: getResponseData
    });

    uppyInstance.on('upload-success', (file, body) => {
        console.log(body);
        uppyInstance.info("Upload successful");
        messageField.value = "{{image}}"+body.responseText;
    });

    uppyInstance.use(Dashboard, {
        trigger: '#uppy-select-files',
        thumbnailWidth: 280,
        showLinkToFileUploadResult: false,
        note: 'Images only up to 3 MB',
        closeModalOnClickOutside: true,
        closeAfterFinish: true
    })
}

const getResponseData = (responseText, response) => {
    return response;
};