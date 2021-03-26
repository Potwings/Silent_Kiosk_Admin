var service = (function() {
	
	function deleteNotice(nno){
		
		return fetch("/notice/"+nno,{
				method : 'post'
		}).then(res => res.text())
	}

        return {deleteNotice:deleteNotice}

    }())